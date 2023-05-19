package com.bankly.core.common.decoder;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&\u00a8\u0006\u0005"}, d2 = {"Lcom/bankly/core/common/decoder/StringDecoder;", "", "decodeString", "", "encodedString", "common_debug"})
public abstract interface StringDecoder {
    
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String decodeString(@org.jetbrains.annotations.NotNull
    java.lang.String encodedString);
}