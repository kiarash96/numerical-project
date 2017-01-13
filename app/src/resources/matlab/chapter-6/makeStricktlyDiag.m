function A = makeStricktlyDiag(a,b)
fail = 0;
message = '';
a(:, size(a,2)+1 ) = b;
[m,n]=size(a);
for j=1:m-1
    for z=2:m
        if a(j,j)==0
            t=a(j,:);a(j,:)=a(z,:);
            a(z,:)=t;
        end
    end
    for i=j+1:m
        if a(j,j)~=0
        a(i,:)=a(i,:)-a(j,:)*(a(i,j)/a(j,j));
        end
    end
    
end
for i =m: -1:2
    for j = i-1 :-1:1
        if a(i,i)~=0
      
        a(j,:)=a(j,:)-a(i,:)*(a(j,i)/a(i,i));
        end
    end
end

maximum= zeros(1,m);
for i=1:m
    v = a(i,:);
    maximum(i) = max(abs(v));
end
mini = min(maximum);
maxi = max(maximum);
x = mini/(maxi*m^2);
for i=1 :m
    for j=1:m
        if i~=j
            a(j,:)=a(j,:)+a(i,:)*x;

        end
    end
end
A = a;
end