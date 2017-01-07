function [fail, message,history , final ]=Eigen(A)
fail = 0;
message = '';
history =0;
final = 0;
[m, n] = size(A);

syms l
B= l;
for i =1:m
    for j=1:m
        B(i, j) = A(i,j);
    end
    
    B(i,i) = A(i,i) - l;
end
c = coeffs(det(B));
p = zeros(1,length(c));
for i=1: length(c)
    
p(i) = c(length(c) - i+1);
    
end
root = roots(p);
final = zeros(length(root), m+1);
final(:,1) = root;
for v = 1: length(root)
    C = subs(B , root(v));
    for j=1:m-1
        for z=2:m
            if C(j,j)==0
                t=C(j,:);C(j,:)=C(z,:);
                C(z,:)=t;
            end
        end
        for i=j+1:m
             C(i,:) = C(i,:)-C(j,:)*(C(i,j)/C(j,j));
           
        end
    end
    
    for x = m:-1:1
        if norm(C(x , :)) <0.0001
            b = -C(1:x-1 , x);
            C = C(1: x-1, 1: x-1);
            mark = x-1;
        end
    end
    C(:, size(C,2)+1 ) = b;
    x=zeros(1,mark);
    for s=mark:-1:1
    c=0;
    for k=2:mark
        c=c+C(s,k)*x(k);
    end
    x(s)=(C(s,mark+1)-c)/C(s,s);
    end
    x(size(x,1)+1,1) = 1;
    final(:,v+1) = x;
end

end
