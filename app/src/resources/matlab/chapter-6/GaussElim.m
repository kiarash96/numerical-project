function [fail, message , history , final ] = GaussElim(a,b)
fail = 0;
message = '';
a(:, size(a,2)+1 ) = b;
[m,n]=size(a);
%defining history structure in this method
history = zeros(m, m+1 ,m );
for j=1:m-1
    for z=2:m
        if a(j,j)==0
            t=a(j,:);a(j,:)=a(z,:);
            a(z,:)=t;
        end
    end
    for i=j+1:m
        a(i,:)=a(i,:)-a(j,:)*(a(i,j)/a(j,j));
    end
    %history is a 3 dimensianol matrix, third parameter indicateds the
    %number of element in the history. there are totally m elements in the
    %history.
    history ( :, :, j ) = a;
end
x=zeros(1,m);
for s=m:-1:1
    c=0;
    for k=2:m
        c=c+a(s,k)*x(k);
    end
    x(s)=(a(s,n)-c)/a(s,s);
end
final = x;
end