function [ ansX , ansY ] = DE_Euler( equation1 ,equation2 ,  iniX, iniY, iniT ,h , n )
f = inline(equation1 , 'x' , 'y' , 't');
g = inline(equation2 , 'x' , 'y' , 't');
ansX(1) = iniX;
ansY(1) = iniY;
t(1)    = iniT;
for k=1:n
   t(k+1) = t(k) + h;
   ansX(k+1) = ansX(k)+ h * f(ansX(k) , ansY(k) , t(k)); 
   ansY(k+1) = ansY(k)+ h * g(ansX(k) , ansY(k) , t(k)); 
end

