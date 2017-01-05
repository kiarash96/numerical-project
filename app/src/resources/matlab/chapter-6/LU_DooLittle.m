function [fail, message,history , final ]=LU_DooLittle(A, b)
     fail = 0;
     message = '';
     m = length(b);

    %defining history structure in this method
    history = zeros(m, m ,3 );


    [L, U, p] = lu(A);
    A = p * A;
    b = p * b;
    [L, U] = lu(A);
    
    
    %first and second elements in history
    history (:,:, 1) = L;
    history (:,:, 2) = U;

    

   
    y = zeros(m,1);
    for i = 1: m
       y(i) = (b(i)- L(i, :) * y)/L(i, i);
    end
    %third element in histor
    history (:,1, 3) = y;
    z = zeros(m,1);
    for i =  size(b,1): -1:1
      z(i)   = (y(i) - U(i, :)*z)/ U(i, i);
    end
    final = z;
    
end
