/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package liker;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-01-24")
public class IndexTargetResult implements org.apache.thrift.TBase<IndexTargetResult, IndexTargetResult._Fields>, java.io.Serializable, Cloneable, Comparable<IndexTargetResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IndexTargetResult");

  private static final org.apache.thrift.protocol.TField SCORE_FIELD_DESC = new org.apache.thrift.protocol.TField("score", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField DOC_FIELD_DESC = new org.apache.thrift.protocol.TField("doc", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new IndexTargetResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new IndexTargetResultTupleSchemeFactory());
  }

  public double score; // required
  public Document doc; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SCORE((short)1, "score"),
    DOC((short)2, "doc");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SCORE
          return SCORE;
        case 2: // DOC
          return DOC;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SCORE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SCORE, new org.apache.thrift.meta_data.FieldMetaData("score", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.DOC, new org.apache.thrift.meta_data.FieldMetaData("doc", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Document.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IndexTargetResult.class, metaDataMap);
  }

  public IndexTargetResult() {
  }

  public IndexTargetResult(
    double score,
    Document doc)
  {
    this();
    this.score = score;
    setScoreIsSet(true);
    this.doc = doc;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IndexTargetResult(IndexTargetResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.score = other.score;
    if (other.isSetDoc()) {
      this.doc = new Document(other.doc);
    }
  }

  public IndexTargetResult deepCopy() {
    return new IndexTargetResult(this);
  }

  @Override
  public void clear() {
    setScoreIsSet(false);
    this.score = 0.0;
    this.doc = null;
  }

  public double getScore() {
    return this.score;
  }

  public IndexTargetResult setScore(double score) {
    this.score = score;
    setScoreIsSet(true);
    return this;
  }

  public void unsetScore() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SCORE_ISSET_ID);
  }

  /** Returns true if field score is set (has been assigned a value) and false otherwise */
  public boolean isSetScore() {
    return EncodingUtils.testBit(__isset_bitfield, __SCORE_ISSET_ID);
  }

  public void setScoreIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SCORE_ISSET_ID, value);
  }

  public Document getDoc() {
    return this.doc;
  }

  public IndexTargetResult setDoc(Document doc) {
    this.doc = doc;
    return this;
  }

  public void unsetDoc() {
    this.doc = null;
  }

  /** Returns true if field doc is set (has been assigned a value) and false otherwise */
  public boolean isSetDoc() {
    return this.doc != null;
  }

  public void setDocIsSet(boolean value) {
    if (!value) {
      this.doc = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SCORE:
      if (value == null) {
        unsetScore();
      } else {
        setScore((Double)value);
      }
      break;

    case DOC:
      if (value == null) {
        unsetDoc();
      } else {
        setDoc((Document)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SCORE:
      return getScore();

    case DOC:
      return getDoc();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SCORE:
      return isSetScore();
    case DOC:
      return isSetDoc();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof IndexTargetResult)
      return this.equals((IndexTargetResult)that);
    return false;
  }

  public boolean equals(IndexTargetResult that) {
    if (that == null)
      return false;

    boolean this_present_score = true;
    boolean that_present_score = true;
    if (this_present_score || that_present_score) {
      if (!(this_present_score && that_present_score))
        return false;
      if (this.score != that.score)
        return false;
    }

    boolean this_present_doc = true && this.isSetDoc();
    boolean that_present_doc = true && that.isSetDoc();
    if (this_present_doc || that_present_doc) {
      if (!(this_present_doc && that_present_doc))
        return false;
      if (!this.doc.equals(that.doc))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_score = true;
    list.add(present_score);
    if (present_score)
      list.add(score);

    boolean present_doc = true && (isSetDoc());
    list.add(present_doc);
    if (present_doc)
      list.add(doc);

    return list.hashCode();
  }

  @Override
  public int compareTo(IndexTargetResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetScore()).compareTo(other.isSetScore());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScore()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.score, other.score);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDoc()).compareTo(other.isSetDoc());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDoc()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.doc, other.doc);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("IndexTargetResult(");
    boolean first = true;

    sb.append("score:");
    sb.append(this.score);
    first = false;
    if (!first) sb.append(", ");
    sb.append("doc:");
    if (this.doc == null) {
      sb.append("null");
    } else {
      sb.append(this.doc);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (doc != null) {
      doc.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class IndexTargetResultStandardSchemeFactory implements SchemeFactory {
    public IndexTargetResultStandardScheme getScheme() {
      return new IndexTargetResultStandardScheme();
    }
  }

  private static class IndexTargetResultStandardScheme extends StandardScheme<IndexTargetResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IndexTargetResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SCORE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.score = iprot.readDouble();
              struct.setScoreIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DOC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.doc = new Document();
              struct.doc.read(iprot);
              struct.setDocIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, IndexTargetResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SCORE_FIELD_DESC);
      oprot.writeDouble(struct.score);
      oprot.writeFieldEnd();
      if (struct.doc != null) {
        oprot.writeFieldBegin(DOC_FIELD_DESC);
        struct.doc.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IndexTargetResultTupleSchemeFactory implements SchemeFactory {
    public IndexTargetResultTupleScheme getScheme() {
      return new IndexTargetResultTupleScheme();
    }
  }

  private static class IndexTargetResultTupleScheme extends TupleScheme<IndexTargetResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IndexTargetResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetScore()) {
        optionals.set(0);
      }
      if (struct.isSetDoc()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetScore()) {
        oprot.writeDouble(struct.score);
      }
      if (struct.isSetDoc()) {
        struct.doc.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IndexTargetResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.score = iprot.readDouble();
        struct.setScoreIsSet(true);
      }
      if (incoming.get(1)) {
        struct.doc = new Document();
        struct.doc.read(iprot);
        struct.setDocIsSet(true);
      }
    }
  }

}

