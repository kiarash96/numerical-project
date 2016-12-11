function [ roots, valueInRoots ,message, fail] = newtonRaphson(func,p0 , step, tol)

str = '@(x)';
func = strcat(str, func);
f = str2func(func);
syms x
df = diff(f,x) ;           %differential of f(x)
df = symfun(df, x);

d2f = diff(df,x) ;           %differential of f(x)
d2f = symfun(d2f, x);



roots=0;
valueInRoots=0;
message = '';
fail=0;
i=1;
if(abs(f(p0)*d2f(p0) / f(p0)^2 )>1)
    fail = 1;
    message= 'Method fails because convegence condition does not hold.';
    return;   
end
    
    
if (checkNotReal(f(p0))==1)
    fail = 1;
    message= 'Method fails because value of function is not a real number in some points.';
    return;
end
if(step==-1)
    step = 100;
end
while i <= step
    p = p0 - (f(p0)/df(p0));          %Newton-Raphson method
     
    roots(i)=p;
    valueInRoots(i)=f(p);
    
     if(checkNotReal(valueInRoots)==1)
        fail = 1;
        message= 'Method fails because value of function is not a real number in some points.';
        return;
     end
    
     
    if (abs(p - p0)) <= tol                     
        return;
    end
 
    i = i + 1;
    p0 = p;             %update p0
end
end