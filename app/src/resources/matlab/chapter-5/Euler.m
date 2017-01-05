function [ ansX , ansY ] =Euler ( equation , iniX, iniY, h , n )
f = inline(equation,'x','y');
ansX(1) = iniX;
ansY(1) = iniY;
for k = 1:n
    ansX(k+1) = ansX(k) + h;
    ansY(k+1) = ansY(k)+ f(ansX(k),ansY(k))* h;
end

end

