function [ ansX , ansY ] = Four_RungeKutta( equation , iniX, iniY, h , n )
syms x y 
f = inline(equation , 'x' , 'y');
F = sym(equation);
ansX(1) = iniX;
ansY(1) = iniY;

for i = 1:n
   k1(i) = f(ansX(i) , ansY(i));
   k2(i) = f(ansX(i)+ h/2 , ansY(i)+h *k1(i)/2);
   k3(i) = f(ansX(i)+ h/2 , ansY(i)+h *k2(i)/2);
   k4(i) = f(ansX(i)+ h , ansY(i)+h*k3(i));
   ansX(i+1) = ansX(i)+ h;
   ansY(i+1) = ansY(i)+ h/6 *(k1(i) + 2* k2(i) + 2 * k3(i) + k4(i));
end
end



