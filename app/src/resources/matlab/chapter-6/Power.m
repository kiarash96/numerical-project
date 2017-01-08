function [fail, message,history , final ]=Power(A, x, t)
fail = 0;
message = '';
history = 0;
m=0;
n=length(x);
y_final=x;
i = 1;
history = zeros ( size(x,1), t);
while(i<=t)
     history(:, i)=y_final; %// Change - Save old eigenvector
     y_final=A*y_final;
     m=max(y_final);
     y_final=y_final/m;
     i = i+1;
end
final = y_final;
history = transpose(history)
end
