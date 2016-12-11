function [ flag , result ] = newtonForwardBackward( X , Y , x , m )
   n = length(X);
   flag = 1;
   digits(m);
   for i = 2:n-1
       if(vpa(X(i+1) - X(i)) ~= vpa(X(i)-X(i-1)))
           flag = 0;
           result = 'Wrong Input Vector';
       end
   end
    if(flag == 1)
       F = ones(n , n);
       h = X(2)-X(1);
       %Getting Table
        for i = 1:n
           F(i,1) = Y(i);
        end
        for i = 2:n
           for j = 2:i
              F(i,j)=vpa(F(i,j-1)-F(i-1,j-1));
           end
        end
        %End of Getting Table
         if(x < median(X))
             r = vpa((x - X(1))/h);
             y = F(1 , 1);
             c = 1;
             for i = 1:n-1
                 c = c * (r-i+1);
                 y = y + c*F(i+1 , i+1)/factorial(i);
                 result = y ;
             end
         else
             r = vpa((x - X(n))/h);
             y = F(n , 1);
             c = 1;
             for i = 1:n-1
                 c = c * (r+i-1);
                 y = y + c*F(n , i+1)/factorial(i);
                 result = y ;
             end
         end
          figure
          plot(X , Y  , 'b--o' , x , y , 'g--o' );
          title('Interpolated Curve Using Newton Forward-Backward Method');
          xlabel('X values');
          ylabel('Y values');
          legend('given values' , 'interpolated value');
    end
     
end

