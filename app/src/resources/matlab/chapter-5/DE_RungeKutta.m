function [ ansX , ansY, ansZ ] = DE_RungeKutta( equation1 ,equation2 ,  iniX, iniY, iniT ,h , n )
f = inline(equation1 , 'x' , 'y' , 'z');
g = inline(equation2 , 'x' , 'y' , 'z');
ansX = [iniX:h:iniX + n * h];
ansY(1) = iniX;
ansZ(1) = iniY;
t(1)    = iniT;
for k=1:n
   t(k+1) = t(k) + h;
   f1(k) = f(ansY(k) , ansZ(k) , t(k)); 
   g1(k) = g(ansY(k) , ansZ(k) , t(k)); 
   f2(k) = f(ansY(k)+h * f1(k)/2 , ansZ(k)+h*g1(k)/2  , t(k)+h/2);
   g2(k) = g(ansY(k)+h * f1(k)/2 , ansZ(k)+h*g1(k)/2  , t(k)+h/2); 
   f3(k) = f(ansY(k)+h * f2(k)/2 , ansZ(k)+h*g2(k)/2  , t(k)+h/2);
   g3(k) = g(ansY(k)+h * f2(k)/2 , ansZ(k)+h*g2(k)/2  , t(k)+h/2); 
   f4(k) = f(ansY(k)+h * f3(k)   , ansZ(k)+h*g3(k)    , t(k)+h);
   g4(k) = g(ansY(k)+h * f3(k)   , ansZ(k)+h*g3(k)    , t(k)+h); 
   
   ansY(k+1) = ansY(k)+ h /6 *(f1(k) + 2 * f2(k) + 2 * f3(k) + f4(k));
   ansZ(k+1) = ansZ(k)+ h /6 *(g1(k) + 2 * g2(k) + 2 * g3(k) + g4(k)); 
end

end

