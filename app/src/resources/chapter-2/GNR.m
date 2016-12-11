function [ roots, valueInRoots ,message, fail] = GNR(funcs,n,p0, step, tol)


        roots=zeros(n , step);
        valueInRoots=zeros(n , step);
        message = '';
        fail=0;


    for i=1:n
        vars(i) = sym(strcat('a',num2str(i-1)));
    end


    for j=1:n 
    for i=1:n
        J(j,i)=diff(funcs(j),vars(i));
    end 
    end 


    dmain=0.1*ones(n,1); 

    i=1;
    while(i<step ||any(abs(dmain))>tol) 
        f1=subs(funcs,vars,p0) ;
        J1=subs(J,vars,p0);
        dmain=pinv(J1)*f1'; 
        p0=p0-dmain';
        roots(:,i ) = p0;
        i=i+1; 
    end 
%valueInRoots is not set.
    
end