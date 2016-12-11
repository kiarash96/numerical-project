package project.app.utility;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;

/**
 * Created by kiarash on 12/7/16.
 */
public class MatlabConnection {

    String rootPath;
    MatlabProxy proxy;

    public MatlabConnection() {
        rootPath = null;
        proxy = null;
    }

    public void open() throws MatlabConnectionException {
        MatlabProxyFactoryOptions.Builder builder = new MatlabProxyFactoryOptions.Builder();
        builder.setUsePreviouslyControlledSession(true);
        MatlabProxyFactory factory = new MatlabProxyFactory(builder.build());
        proxy = factory.getProxy();
    }

    public void close() {
        proxy.disconnect();
    }

    public void setRootPath(String rootPath) throws MatlabInvocationException {
        this.rootPath = rootPath;
        this.cd(rootPath);
    }

    public void eval(String cmd) throws MatlabInvocationException {
        proxy.eval(cmd);
    }

    public MatlabStruct feval(String relPath, String funcname,
                              MatlabStruct args, String... retNames)
            throws MatlabInvocationException {

        if (rootPath == null)
            throw new RuntimeException("feval is called before setting root directory");

        this.cd(relPath);
        Object[] arr = proxy.returningFeval(funcname,
                retNames.length, args.toArray());

        MatlabStruct res = new MatlabStruct(retNames);
        for (int i = 0; i < retNames.length; i ++) {
            Object v = arr[i];
            if (v instanceof String)
                res.set(retNames[i], (String) v);
            else { // double[]
                double[] mat = (double[]) v;
                if (mat.length == 1)
                    res.set(retNames[i], mat[0]);
                else
                    res.set(retNames[i], mat);
            }
        }

        this.cd(rootPath);
        return res;
    }

    private void cd(String dir) throws MatlabInvocationException {
        this.eval("cd " + dir);
    }

}
