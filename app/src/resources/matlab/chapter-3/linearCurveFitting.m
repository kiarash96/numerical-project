function [ a, b ] = linearCurveFitting( X , Y , m )
 
 n = length(X);
 sumx = 0;sumy = 0; sumx2 = 0; sumxy = 0;
     for i = 1: n
         sumx = sumx + X(i);
     end
     for i = 1: n
         sumy = sumy + Y(i);
     end
      for i = 1: n
         sumx2 = X(i) * X(i)+sumx2;
      end
     for i = 1: n
         sumxy = X(i) * Y(i)+sumxy;
     end
    
     A = [n  sumx ; sumx  sumx2];
     B = [sumy ;sumxy];
     C = inv(A)* B;  
     b = C(1 ,1)
     a = C(2 ,1)
end

