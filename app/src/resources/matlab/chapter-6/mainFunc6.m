function [fail, message ,history, final] = mainFunc6(A,b, x0, t, i)

s=size(A);
res=zeros(s(2),size(A{1},2));
for w=1:s(2)
    for j=1:size(A{1},2)
        res(w,j)=A{w}(j);
    end
end
A = res;
b = transpose(b);
x0 = transpose(x0);


A
b
x0
t
i
%prepare error messages
fail = 0;
message = '';
history =0;
final = 0;
[m,n] = size(A);
if m> n
    fail = 1;
    messsage = 'You have entered more equations than number of unknowns. ';
    return;
end
if m< n
    fail = 1;
    messsage = 'You have entered less equations than number of unknowns. ';
    return;
end

if det(A)==0
    fail = 1;
    messsage = 'Det of matrix of coefficiants is zero. ';
end

if i == 1
  [fail , message, history , final ] = Cramer(A,b);
end
if i == 2
  [fail, message , history , final ] = GaussElim(A,b);
end

if i==3
  [fail, message,history , final ]=LU_DooLittle(A, b);
end

if i == 4
  [fail, message,history , final ]=Cholesky(A, b);
end
if i == 5
  %[fail, message,history , final ]=
end
if i == 6
  [fail, message,history , final ]=Jacobi(A, b, x0, t);
end
if i == 7
  [fail, message,history , final ]=GS(A, b, x0, t);
end

end
