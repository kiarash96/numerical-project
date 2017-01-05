function [ ansX , ansY ] =Taylor( equation , iniX, iniY, h , n )
syms x y 
f = inline(equation , 'x' , 'y');
F = sym(equation);
fX = diff(F , x);
fx = inline(fX , 'x' , 'y');

fY = diff(F , y);
fy = inline(fY , 'x' , 'y');
% ansX = 1:n;
% ansY = 1:n;
ansX(1) = iniX;
ansY(1) = iniY;
for k =1:n
    ansX(k+1) = ansX(k)+h;
    g  =  f(ansX(k) , ansY(k));
    gx = fx(ansX(k) , ansY(k));
    gy = fy(ansX(k) , ansY(k));
    ansY(k+1) = ansY(k)+ h * (g + h / 2 * (gx + g * gy));
end
    
end

