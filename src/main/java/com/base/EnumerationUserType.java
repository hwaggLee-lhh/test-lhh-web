package com.base;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class EnumerationUserType implements CompositeUserType {
	public static final Logger log = Logger
			.getLogger(EnumerationUserType.class);

	public String[] getPropertyNames() {
		return new String[] { "code" };
	}

	public Type[] getPropertyTypes() {
		return new Type[] { StringType.INSTANCE };
	}

	public Object getPropertyValue(Object component, int property) {
		Enumeration enumeration = (Enumeration) component;
		switch (property) {
		case 0:
			return enumeration.getCode();
		default:
			throw new IllegalArgumentException("unknown property:" + property);
		}
	}

	public void setPropertyValue(Object component, int property, Object value) {
		Enumeration enumeration = (Enumeration) component;
		switch (property) {
		case 0:
			enumeration.setCode((String) value);
		default:
			throw new IllegalArgumentException("unknown property:" + property);
		}
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner) throws SQLException {
		String code = (String) StringType.INSTANCE.nullSafeGet(rs, names,
				session, owner);
		return new Enumeration(code);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws SQLException {
		if (value != null) {
			value = ((Enumeration) value).getCode();
		}
		StringType.INSTANCE.nullSafeSet(st, value, index, session);
	}

	/**
	 * 当对象从二级缓存中取出时调用
	 */
	public Object assemble(Serializable cached, SessionImplementor session,
			Object owner) {
		return deepCopy(cached);
	}

	/**
	 * 当对象进入二级缓存是调用
	 */
	public Serializable disassemble(Object value, SessionImplementor session) {
		return (Serializable) deepCopy(value);
	}

	/**
	 * 处理托管对象的合并
	 */
	public Object replace(Object original, Object target,
			SessionImplementor session, Object owner) {
		if (original == null) {
			return null;
		}
		return ((Enumeration) original).clone();
	}

	public Object deepCopy(Object value) {
		if (value == null) {
			return null;
		}
		return ((Enumeration) value).clone();
	}

	public boolean isMutable() {
		return true;
	}

	public Class<Enumeration> returnedClass() {
		return Enumeration.class;
	}

	public boolean equals(Object x, Object y) {
		return x == y || (x != null && y != null && x.equals(y));
	}

	public int hashCode(Object obj) {
		return obj.hashCode();
	}
}
