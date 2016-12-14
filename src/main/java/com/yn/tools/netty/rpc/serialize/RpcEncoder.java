package com.yn.tools.netty.rpc.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.yn.tools.netty.rpc.factory.KryoPoolFactory;
import com.yn.tools.netty.rpc.model.Response;

import java.io.ByteArrayOutputStream;

/**
 * Created by yangnan on 2016/12/14.
 */
public class RpcEncoder extends AbstractEncoder {

    private KryoPool pool;

    public RpcEncoder(Class<Response> responseClass) {
        super(responseClass);
        pool = KryoPoolFactory.getKryoPoolInstance();
    }

    public byte[] encode(Object data) {
        Output output = null;
        Kryo kryo = pool.borrow();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            output = new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output, data);
            return output.toBytes();
        } finally {
            pool.release(kryo);
            if (output != null) {
                output.close();
            }
        }
    }
}
