function  [highs, lows, roots, valueInRoots ,message, fail]= bisection(func, low, high,step, tol)
str = '@(x)';
func = strcat(str, func);
f = str2func(func);
y1 = f( low);
y2 = f( high);

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
if (checkNotReal(y1)==1 || checkNotReal(y2) ==1)
    fail = 1;
    message= 'Method fails because value of function is not a real number in some points.';
    return;
end
if y1 * y2 > 0
   fail = 1;
    message= 'Bolzano condition does not hold.';
   return;
end 

% Work with the limits modifying them until you find
% a function close enough to zero.
i = 1; 
if(step==-1)
    step = 100;
end
while i <= step
 
    roots(i) = (high + low)/2;
    highs(i)=high;
    lows(i) = low;
    y3 = f( roots(i));
    valueInRoots(i)= y3;
    if(checkNotReal(valueInRoots)==1)
        fail = 1;
        message= 'Method fails because value of function is not a real number in some points.';
        return;
    end
    
    if y3 == 0
        return;
    end
    
    if(abs(f(high) - f(low)) <= tol)
        return ;
    end
       
    % Update the limits
    if y1 * y3 > 0
        low = roots(i);
        y1 = y3;
    else
        high = roots(i);
    end
    
       i = i + 1;
end
end

