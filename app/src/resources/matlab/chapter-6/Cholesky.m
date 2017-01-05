function [fail, message,history , final ]=Cholesky(A, b)
     fail = 0;
     message = '';
     history = 0;
     final = 0;
     m = length(b);
    [U, p] = chol(A);
    %defining history structure in this method
    history = zeros(m, m ,3 );

    if p~=0
        fail = 1;
        message = 'The matrix of coefficiant is not positive definite.'
        return;
    end
     L = U';
  
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
