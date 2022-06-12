/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.api;

import com.google.gson.GsonBuilder;
import net.ddns.minersonline.BetterCC.api.bus.device.object.Callback;
import net.ddns.minersonline.BetterCC.api.bus.device.rpc.RPCMethod;
import net.ddns.minersonline.BetterCC.api.imc.RPCMethodParameterTypeAdapter;

import java.lang.reflect.Type;

public final class API {
    public static final String MOD_ID = "better_cc";

    ///////////////////////////////////////////////////////////////////

    /**
     * IMC message for registering Gson type adapters for method parameter serialization and
     * deserialization.
     * <p>
     * Must be called with a supplier that provides an instance of {@link RPCMethodParameterTypeAdapter}.
     * <p>
     * It can be necessary to register additional serializers when implementing {@link RPCMethod}s
     * that use custom parameter types.
     *
     * @see GsonBuilder#registerTypeAdapter(Type, Object)
     * @see RPCMethod
     * @see Callback
     */
    public static final String IMC_ADD_RPC_METHOD_PARAMETER_TYPE_ADAPTER = "addRPCMethodParameterTypeAdapter";
}
