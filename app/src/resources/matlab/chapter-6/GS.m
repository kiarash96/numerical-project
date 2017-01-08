function [fail, message,history , final ]=GS(A, b, x0, t)
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
       temp = makeStricktlyDiag(A,b);
       history (:, t+1:m+t+1) = temp;
       
        A = temp(:, 1:m);
       b= temp(:, m+1);
    end 
 end
 R = A;
 for i =1 :m
    L(i,1:i) = A(i,1:i);
 end
 
 U = A-L;
 x = x0;
 
 for i=1:t
     x = inv(L)* (b - U*x);
     history(:,i) = x;
     
 end
 final = x;
 history = transpose(history)
 end
