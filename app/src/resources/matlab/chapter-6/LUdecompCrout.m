 function [fail, message,history , final ] = LUdecompCrout(A, b)
     fail = 0;
      message = '';
      m = length(b);

    %defining history structure in this method
    history = zeros(m, m ,3 );

        [R, C] = size(A);
        for i = 1:R
            L(i, 1) = A(i, 1);
            U(i, i) = 1;
        end
        for j = 2:R
            U(1, j) = A(1, j) / L(1, 1);
        end
        for i = 2:R
            for j = 2:i
                L(i, j) = A(i, j) - L(i, 1:j - 1) * U(1:j - 1, j);
            end
            
            for j = i - 1:R
                U(i, j) = (A(i, j) - L(i, 1:i - 1) * U(1:i - 1, j)) / L(i, i);
            end
        end
        
        
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