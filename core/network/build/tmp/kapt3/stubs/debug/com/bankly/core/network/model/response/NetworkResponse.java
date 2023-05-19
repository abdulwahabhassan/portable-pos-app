package com.bankly.core.network.model.response;

/**
 * Wrapper for data provided from the [BanklyBaseUrl]
 */
@kotlinx.serialization.Serializable
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 5*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u000245B[\b\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0002\u0010\u0010BG\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\r\u00a2\u0006\u0002\u0010\u0011J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u0010\u0010\u001f\u001a\u0004\u0018\u00018\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u0010\u0010 \u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010!\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u0011\u0010#\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\rH\u00c6\u0003Jb\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00018\u00002\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0010\b\u0002\u0010\f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\rH\u00c6\u0001\u00a2\u0006\u0002\u0010%J\u0013\u0010&\u001a\u00020\u00062\b\u0010\'\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010(\u001a\u00020\u0004H\u00d6\u0001J\t\u0010)\u001a\u00020\nH\u00d6\u0001J;\u0010*\u001a\u00020+\"\u0004\b\u0001\u0010,2\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H,0\u00002\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u0002H,03H\u00c7\u0001R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u001b\u0010\u0013R\u0019\u0010\f\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d\u00a8\u00066"}, d2 = {"Lcom/bankly/core/network/model/response/NetworkResponse;", "T", "", "seen1", "", "hasResult", "", "result", "successful", "resultType", "", "message", "validationMessages", "", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/Boolean;Ljava/lang/Object;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/Boolean;Ljava/lang/Object;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getHasResult", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getMessage", "()Ljava/lang/String;", "getResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getResultType", "getSuccessful", "getValidationMessages", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/Boolean;Ljava/lang/Object;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/bankly/core/network/model/response/NetworkResponse;", "equals", "other", "hashCode", "toString", "write$Self", "", "T0", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "typeSerial0", "Lkotlinx/serialization/KSerializer;", "$serializer", "Companion", "network_debug"})
public final class NetworkResponse<T extends java.lang.Object> {
    @org.jetbrains.annotations.Nullable
    private final java.lang.Boolean hasResult = null;
    @org.jetbrains.annotations.Nullable
    private final T result = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Boolean successful = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String resultType = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String message = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> validationMessages = null;
    @org.jetbrains.annotations.NotNull
    public static final com.bankly.core.network.model.response.NetworkResponse.Companion Companion = null;
    
    public NetworkResponse(@org.jetbrains.annotations.Nullable
    java.lang.Boolean hasResult, @org.jetbrains.annotations.Nullable
    T result, @org.jetbrains.annotations.Nullable
    java.lang.Boolean successful, @org.jetbrains.annotations.Nullable
    java.lang.String resultType, @org.jetbrains.annotations.Nullable
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> validationMessages) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean getHasResult() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final T getResult() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean getSuccessful() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getResultType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getValidationMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final T component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Boolean component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.bankly.core.network.model.response.NetworkResponse<T> copy(@org.jetbrains.annotations.Nullable
    java.lang.Boolean hasResult, @org.jetbrains.annotations.Nullable
    T result, @org.jetbrains.annotations.Nullable
    java.lang.Boolean successful, @org.jetbrains.annotations.Nullable
    java.lang.String resultType, @org.jetbrains.annotations.Nullable
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> validationMessages) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic
    public static final <T0 extends java.lang.Object>void write$Self(@org.jetbrains.annotations.NotNull
    com.bankly.core.network.model.response.NetworkResponse<T0> self, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.KSerializer<T0> typeSerial0) {
    }
    
    /**
     * Wrapper for data provided from the [BanklyBaseUrl]
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0001\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0002B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004B\u0015\b\u0017\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u000fH\u00d6\u0001\u00a2\u0006\u0002\u0010\u0010J\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\u001f\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u00d6\u0001J\u0018\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u000fH\u00d6\u0001\u00a2\u0006\u0002\u0010\u0010R\u0014\u0010\b\u001a\u00020\t8VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u00068BX\u00c2\u0005\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001a"}, d2 = {"com/bankly/core/network/model/response/NetworkResponse.$serializer", "T", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/bankly/core/network/model/response/NetworkResponse;", "()V", "typeSerial0", "Lkotlinx/serialization/KSerializer;", "(Lkotlinx/serialization/KSerializer;)V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "getTypeSerial0", "()Lkotlinx/serialization/KSerializer;", "childSerializers", "", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "typeParametersSerializers", "network_debug"})
    @java.lang.Deprecated
    public static final class $serializer<T extends java.lang.Object> implements kotlinx.serialization.internal.GeneratedSerializer<com.bankly.core.network.model.response.NetworkResponse<T>> {
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.bankly.core.network.model.response.NetworkResponse<T> deserialize(@org.jetbrains.annotations.NotNull
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        private final kotlinx.serialization.KSerializer<T> getTypeSerial0() {
            return null;
        }
        
        @java.lang.Override
        public void serialize(@org.jetbrains.annotations.NotNull
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull
        com.bankly.core.network.model.response.NetworkResponse<T> value) {
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    /**
     * Wrapper for data provided from the [BanklyBaseUrl]
     */
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\u00050\u0004\"\u0004\b\u0001\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0004H\u00c6\u0001\u00a8\u0006\b"}, d2 = {"Lcom/bankly/core/network/model/response/NetworkResponse$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/bankly/core/network/model/response/NetworkResponse;", "T0", "typeSerial0", "network_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final <T0 extends java.lang.Object>kotlinx.serialization.KSerializer<com.bankly.core.network.model.response.NetworkResponse<T0>> serializer(@org.jetbrains.annotations.NotNull
        kotlinx.serialization.KSerializer<T0> typeSerial0) {
            return null;
        }
    }
}