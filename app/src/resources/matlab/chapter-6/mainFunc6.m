function [fail, message history, final] = mainFunc6(funcs,b, n)
%prepare error messages
fail = 0;
if size(funcs,1)> n
    fail = 1;
    messsage = 'You have entered more equations than number of unknowns. '
end
if size(funcs,1)< n
    fail = 1;
    messsage = 'You have entered less equations than number of unknowns. '
end

%funcs are vector of anounymous functions
for i=1:size(funcs, 1) %defining a0 to an
  vars(i) = sym(strcat('a',num2str(i-1)));
end
A = zeros(size(funcs, 1), n);
for i=1: size(funcs, 1) %constructiong coeff matrix
   f = funcs(i);
   for j=1 : n
        c  = coeffs(f, vars(j));
        if length(c) ==2
           A (i, j ) = c(2);
        else
             A (i, j ) = 0;
        end
   end
   
end

if det(A)=0
    fail = 1;
    messsage = 'Det of matrix of coefficiants is zero. '
end
end