function [flag ,result] = CurveFitting(X1 , Y1 , m , type) 
digits(m);
flag = 1;
if(type == 1)
    Y = vpa(log(Y1));
    [a1 , b1] = linearCurveFitting(X1 , Y , m );
    a = exp(a1);
    b = b1 ;
    result = strcat(sprintf('%0.5f',double(a)) , ' * 2.7183 ^ ( ' ,  sprintf('%0.5f',double(b)) , ' * x )');
     %figure
     % plot(X1 , Y);
     % title('Exponential Curve Fitting');
     % xlabel('X values');
     % ylabel('Y values');
elseif(type == 2)
    X = vpa(log(X1));
    [a , b ] = linearCurveFitting(X , Y1 , m);
    result = strcat(sprintf('%0.5f',double(a)) , ' * log(x) + ' ,  sprintf('%0.5f',double(b)) );
    %title('Logarithmic Curve Fitting');
    %  xlabel('X values');
    %  ylabel('Y values');
elseif(type == 3)
    X = vpa(1./X1);
    [a , b ] = linearCurveFitting(X , Y1 , m);
     result = strcat(sprintf('%0.5f',double(a)) , ' / x + ' ,  sprintf('%0.5f',double(b)) );
     %title('Inverse1 Curve Fitting');
     %xlabel('X values');
     %ylabel('Y values');
elseif(type == 4)
    Y = vpa(1 ./ Y1);
    [a , b] = linearCurveFitting(X1 , Y , m );
     result = strcat('1 / ( ', sprintf('%0.5f',double(a)) , ' * x + ' ,  sprintf('%0.5f',double(b)), ' )' );
     %title('Inverse2 Curve Fitting');
     %xlabel('X values');
     %ylabel('Y values');
end
end

