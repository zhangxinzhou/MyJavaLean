package com.example.learn02_mybaties_encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.nio.charset.StandardCharsets;
import java.sql.*;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Encrypt.class)
public class EncryptTypeHandler extends BaseTypeHandler<Encrypt> {

    private static final byte[] KEYS = "12345678abcdefgh".getBytes(StandardCharsets.UTF_8);

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Encrypt encrypt, JdbcType jdbcType) throws SQLException {
        if (encrypt == null || encrypt.getValue() == null) {
            preparedStatement.setString(i, null);
            return;
        }
        AES aes = SecureUtil.aes(KEYS);
        String tmp = aes.encryptHex(encrypt.getValue());
        preparedStatement.setString(i, tmp);
    }

    @Override
    public Encrypt getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return decrypt(resultSet.getString(s));
    }

    @Override
    public Encrypt getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return decrypt(resultSet.getString(i));
    }

    @Override
    public Encrypt getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return decrypt(callableStatement.getString(i));
    }

    public Encrypt decrypt(String value) {
        if (null == value) {
            return null;
        }
        return new Encrypt(SecureUtil.aes(KEYS).decryptStr(value));
    }
}
