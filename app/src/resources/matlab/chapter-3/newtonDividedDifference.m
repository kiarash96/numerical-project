function y = newtonDividedDifference(X , Y , x , m)
    digits(m);
    n = size(X,1);
    if n == 1
       n = size(X,2);
    end

    for i = 1:n
       F(i,1) = Y(i);
    end

    for i = 2:n
       for j = 2:i
          F(i,j)= vpa(vpa(F(i,j-1)-F(i-1,j-1))/vpa(X(i)-X(i-j+1)));
       end
    end

    y = F(n,n);
    for i = n-1:-1:1
       y = vpa(vpa (y*(x-X(i))) + F(i,i));
    end
    figure
  plot(X , Y  , 'b--o' , x , y , 'g--o' );
  title('Interpolated Curve Using Newton Divided Difference Method');
  xlabel('X values');
  ylabel('Y values');
  legend('given values' , 'interpolated value');
end
