function [fail, message,history , final ]=Jacobi(A, b, x0, t)
 fail = 0;
 final =0;
 history=0;
 message = '';
 m = length(b);
 B = abs(A);
  
 %defining history structure in this method
 history = zeros(m ,t+m+1);
 %check diagonally dominancy
 temp = zeros (m , m+1);
 for i=1: m
    if 2* abs(A(i,i)) <= sum( B(i, :))
       temp = makeStricktlyDiag(A,b)
       history (:, t+1:m+t+1) = temp;
       
        A = temp(:, 1:m);
       b= temp(:, m+1);
    end 
 end
 R = A;
 for i =1 :m
     R(i,i) = 0;
 end
 D = A-R;
 x = x0;
 
 for i=1:t
     x = inv(D)* (b - R*x);
     history(:,i) = x;
     
 end
 final = x;
 A
x0
 b
 history = transpose(history)
 end
