function [ ansX , ansY, ansZ ] = DE_Euler( equation1 ,equation2 ,  iniX, iniY, iniT ,h , n )
f = inline(equation1 , 'x' , 'y' , 'z');
g = inline(equation2 , 'x' , 'y' , 'z');
ansX = [iniX:h:iniX + n * h];
ansY(1) = iniX;
ansZ(1) = iniY;
t(1)    = iniT;
for k=1:n
   t(k+1) = t(k) + h;
   ansY(k+1) = ansY(k)+ h * f(ansY(k) , ansZ(k) , t(k)); 
   ansZ(k+1) = ansY(k)+ h * g(ansY(k) , ansY(k) , t(k)); 
end

