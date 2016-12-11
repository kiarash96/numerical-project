function [highs, lows, roots, valueInRoots ,message, fail] = falsePosition(func,low , high , step, tol)
str = '@(x)';
func = strcat(str, func);
f = str2func(func);
fx0 = f(low);
fx1= f( high);
x0 = low;
x1 = high;

fail =0;
message = '';
highs=0;
lows=0;
roots=0;
valueInRoots=0;

 
%check if y1 and y2 are  real numbers and interval is valid and bolzano condition holds
if(low >= high)
    fail = 1;
    message= 'Interval is invalid.';
    return;
end
if (checkNotReal(fx0)==1 || checkNotReal(fx1) ==1)
    fail = 1;
    message= 'Method fails because value of function is not a real number in some points.';
    return;
end
if fx0 * fx1 > 0
   fail = 1;
    message= 'Bolzano condition does not hold.';
   return;
end 


if(step==-1)
    step =100;
end
for i=1 :step
    x2 =  x1 - ((x1 - x0)/( fx1 - fx0)) * fx1;
    roots(i) = x2;
    valueInRoots(i) = f(x2);
    lows(i) =x0;
    highs(i) = x1;
    if(checkNotReal(valueInRoots)==1)
        fail = 1;
        message= 'Method fails because value of function is not a real number in some points.';
        return;
    end
    if(abs( fx1- f(x2)) <= tol)
            return ;
    end
    fx2 = f(x2);
    if fx0* fx2<=0
        x1 = x2;
        fx1 = f(x1);
        else
        x0 = x1;
        fx0 = fx1;
        x1 = x2;
        fx1 = f(x1);
    end
	

end
end