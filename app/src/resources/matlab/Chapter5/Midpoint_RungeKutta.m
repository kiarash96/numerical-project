function [ ansX , ansY ] = Midpoint_RungeKutta( equation , iniX, iniY, h , n )
syms x y 
f = inline(equation , 'x' , 'y');
F = sym(equation);
fX = diff(F , x);
fx = inline(fX , 'x' , 'y');

fY = diff(F , y);
fy = inline(fY , 'x' , 'y');

ansX(1) = iniX;
ansY(1) = iniY;

for k = 1:n
    t = 0.5 *(f(ansX(k)+h/2 , ansY(k)+h/2*f(ansX(k) , ansY(k)))+ f(ansX(k),ansY(k)));
    ansX(k+1) = ansX(k)+h;
    ansY(k+1) = ansY(k)+ h * t;
end

end

