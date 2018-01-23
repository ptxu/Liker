package com.liker.services.http.handler;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;

/**
 * @ClassName: HttpNettyHandler
 * @Description: HttpNettyHandler
 * @author xupengtao
 * @date 2018年1月12日 下午6:29:26
 *
 */
public class HttpNettyHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpNettyHandler.class);

    private static final HttpDataFactory FACTORY = new DefaultHttpDataFactory(-1);

    private static final String FAVICON_ICO = "/favicon.ico";

    private final Servlet servlet;

    private final ServletContext servletContext;

    public HttpNettyHandler(Servlet servlet) {
        this.servlet = servlet;
        this.servletContext = servlet.getServletConfig().getServletContext();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (!fullHttpRequest.getDecoderResult().isSuccess()) {
            sendError(channelHandlerContext, BAD_REQUEST);
            return;
        }

        // just support get & post
        if (fullHttpRequest.getMethod() != HttpMethod.GET && fullHttpRequest.getMethod() != HttpMethod.POST) {
            sendError(channelHandlerContext, METHOD_NOT_ALLOWED);
            return;
        }

        // 去除浏览器"/favicon.ico"的干扰
        if (fullHttpRequest.getUri().equals(FAVICON_ICO)) {
            return;
        }

        LOGGER.info(String.format("%s %s", fullHttpRequest.getMethod(), fullHttpRequest.getUri()));

        MockHttpServletRequest servletRequest = createServletRequest(fullHttpRequest);
        MockHttpServletResponse servletResponse = new MockHttpServletResponse();

        this.servlet.service(servletRequest, servletResponse);

        HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
        if (isKeepAlive(fullHttpRequest)) {
            response.headers().set(Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        else {
            response.headers().set(Names.CONNECTION, HttpHeaders.Values.CLOSE);
        }
        byte[] content = servletResponse.getContentAsByteArray();

        for (String name : servletResponse.getHeaderNames()) {
            for (Object value : servletResponse.getHeaderValues(name)) {
                response.headers().add(name, value);
            }
        }
        if (!response.headers().contains(Names.CONTENT_LENGTH)) {
            response.headers().set(Names.CONTENT_LENGTH, content.length);
        }

        // Write the initial line and the header.
        channelHandlerContext.write(response);

        // ByteBuf byteBuf = Unpooled.wrappedBuffer(content);
        response.content().writeBytes(content);
        // byteBuf.release();

        // Write the end marker
        ChannelFuture lastContentFuture = channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

        // Decide whether to close the connection or not.
        if (!isKeepAlive(fullHttpRequest)) {
            // Close the connection when the whole content is written out.
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private MockHttpServletRequest createServletRequest(FullHttpRequest fullHttpRequest) throws IOException {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(fullHttpRequest.getUri()).build();
        MockMultipartHttpServletRequest servletRequest = new MockMultipartHttpServletRequest(this.servletContext);
        int index = fullHttpRequest.getUri().indexOf("?");
        if (index > 0) {
            String queryStr = fullHttpRequest.getUri().substring(index + 1);
            servletRequest.setQueryString(queryStr);
            QueryStringDecoder queryDecoder = new QueryStringDecoder(queryStr, false);
            Map<String, List<String>> uriAttributes = queryDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                servletRequest.addParameter(attr.getKey(), attr.getValue().toArray(new String[] {}));
            }
        }
        servletRequest.setPathInfo(uriComponents.getPath());
        servletRequest.setRequestURI(uriComponents.getPath());
        servletRequest.setMethod(fullHttpRequest.getMethod().name());

        if (uriComponents.getScheme() != null) {
            servletRequest.setScheme(uriComponents.getScheme());
        }
        if (uriComponents.getHost() != null) {
            servletRequest.setServerName(uriComponents.getHost());
        }
        if (uriComponents.getPort() != -1) {
            servletRequest.setServerPort(uriComponents.getPort());
        }

        for (String name : fullHttpRequest.headers().names()) {
            servletRequest.addHeader(name, fullHttpRequest.headers().get(name));
        }

        HttpMethod method = fullHttpRequest.getMethod();
        if (method.equals(HttpMethod.POST)) {
            dealWithContentType(fullHttpRequest, servletRequest);
        }
        else {
            // 其他类型在此不做处理，需要的话可自己扩展
        }
        return servletRequest;
    }

    /**
     * 简单处理常用几种 Content-Type 的 POST 内容（可自行扩展）
     * 
     * @param headers
     * @param content
     * @throws IOException
     * @throws Exception
     */
    private void dealWithContentType(FullHttpRequest fullHttpRequest, MockMultipartHttpServletRequest servletRequest) throws IOException {
        String contentType = fullHttpRequest.headers().get("Content-Type").toString();
        if (contentType.equals("application/json")) {
            ByteBuf buf = fullHttpRequest.content();
            int readable = buf.readableBytes();
            byte[] bytes = new byte[readable];
            buf.readBytes(bytes);
            servletRequest.setContent(bytes);
        }
        else if (contentType.equals("application/x-www-form-urlencoded")) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(FACTORY, fullHttpRequest, Charsets.UTF_8);
            List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
            for (InterfaceHttpData data : datas) {
                if (data.getHttpDataType() == HttpDataType.Attribute) {
                    Attribute attribute = (Attribute) data;
                    servletRequest.addParameter(attribute.getName(), attribute.getValue());
                }
            }
        }
        else if (contentType.contains("multipart/form-data")) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(FACTORY, fullHttpRequest, Charsets.UTF_8);
            List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
            for (InterfaceHttpData data : datas) {
                if (data.getHttpDataType() == HttpDataType.Attribute) {
                    Attribute attribute = (Attribute) data;
                    servletRequest.addParameter(attribute.getName(), attribute.getValue());
                }
                else if (data.getHttpDataType() == HttpDataType.FileUpload) {
                    FileUpload fileUpload = (FileUpload) data;
                    if (fileUpload.isCompleted()) {
                        MockMultipartFile file = new MockMultipartFile(fileUpload.getName(), fileUpload.getFilename(), fileUpload.getContentType(), fileUpload.get());
                        String boundary = "WebKitFormBoundaryDKtHqJIhpAc6n1EL";
                        servletRequest.setContentType("multipart/form-data; boundary=" + boundary);
                        servletRequest.setContent(createFileContent(fileUpload.get(), boundary, fileUpload.getContentType(), fileUpload.getName(), fileUpload.getFilename()));
                        servletRequest.addFile(file);
                    }
                }
            }
        }
        else {
            // do nothing...
        }
    }

    private byte[] createFileContent(byte[] data, String boundary, String contentType, String name, String fileName) {
        byte[] start = ("--" + boundary + "\r\n Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\n" + "Content-type: " + contentType + "\r\n\r\n").getBytes();
        byte[] end = ("--" + boundary + "--").getBytes();
        byte[] array = new byte[data.length + start.length + end.length];
        System.arraycopy(start, 0, array, 0, start.length);
        System.arraycopy(data, 0, array, start.length, data.length);
        System.arraycopy(end, 0, array, start.length + data.length, end.length);
        return array;
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        ByteBuf content = Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8);

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, status, content);
        fullHttpResponse.headers().add(CONTENT_TYPE, "text/plain; charset=UTF-8");

        // Close the connection as soon as the error message is sent.
        ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private static boolean isKeepAlive(HttpMessage message) {
        String connection = message.headers().get(Names.CONNECTION);
        return !(connection == null || connection.equalsIgnoreCase(Values.CLOSE));
    }
}
