function output = errorPropagationWithValue(expression, a, b, c, d, e, f, da, db,dc,dd, de, df)
disp(a)
disp(b)
disp(c)
disp(d)
disp(e)
disp(f)
disp(da)
disp(db)
disp(dc)
disp(dd)
disp(de)
disp(df)

format long;
numbers = a;
errors= a;
numbersString='' ;
errorsString='';
numbers=a;
answeres= a;
erransweres= a;
k=1;
n = size(expression,2);
i = 1;
finish = 1;
while(i <= n)
    if expression(i) == '#'
        for j = i+1 : n
            if expression(j) == '#'
                break;
            end
        end
        %sprintf( 'i%d',i)
        %sprintf( 'j%d',j)
        if expression(i+1:j-1)=='a'
            numbers(finish) = a;
            errors(finish ) = da;
            finish = finish +1;
        
        elseif expression(i+1:j-1)=='b'
            numbers(finish) = b;
            errors (finish) = db;
            finish = finish+1;
            
        elseif expression(i+1:j-1)=='c'
            numbers(finish) = c;
            errors (finish) = dc;
            finish= finish+1;
        elseif expression(i+1:j-1)=='d'
            numbers(finish) = d;
            errors (finish) = dd;
            finish = finish +1;
        elseif expression(i+1:j-1)=='e'
            numbers(finish) = e;
            errors (finish )= de;
            finish  = finish +1;
        
        elseif expression(i+1:j-1)=='f'
            numbers(finish) = f;
            errors(finish) = df;
            finish = finish+1;
        else
            numbers(finish) = str2double(expression(i+1:j-1));
            errors(finish) = 0;
            finish = finish +1;
        end
        i = j+1;
    else 
         
       
         if expression(i)=='+'
           errors (finish-2) = errors (finish-1)+errors(finish-2);
           erransweres(k) = errors(finish-2);
           if(isnumeric(a))
           numbersString  = strcat(numbersString, 'e(',num2str(numbers (finish-1)), '+', num2str(numbers (finish-2)),') =   ', num2str(errors(finish-2)),'\\' );
           else
               numbersString  = strcat(numbersString, 'e(',char(numbers (finish-1)), '+', char(numbers (finish-2)),') =   ', char(errors(finish-2)),'\\' );
           end
           numbers(finish-2) =  numbers (finish-1) + numbers (finish-2);
           answeres(k) = numbers(finish-2);
           k=k+1;
           finish = finish-1;
        
        elseif expression(i)=='*'
           errors(finish-2) =  errors(finish -1) * numbers(finish-2) + errors (finish-2) * numbers(finish-1) ;
           erransweres(k) = errors(finish-2);           
           if(isnumeric(a))
           numbersString  = strcat(numbersString, 'e(', num2str(numbers (finish-1)), '*', num2str(numbers (finish-2)),') =   ', num2str(errors(finish-2)),'\\' );
           else
               numbersString  = strcat(numbersString, 'e(',char(numbers (finish-1)), '*', char(numbers (finish-2)),') =   ', char(errors(finish-2)),'\\' );
           end
           numbers(finish-2) =  numbers (finish-1) * numbers (finish-2);
           answeres(k) = numbers(finish-2);
           k = k+1;
           finish = finish-1;
           
        
        elseif expression(i)=='/'
           errors(finish-2) =  (errors(finish -1) * numbers(finish-2) + errors (finish-2) * numbers(finish-1))/numbers(finish-1)^2;
           erransweres(k) = errors(finish-2);           
           if(isnumeric(a))
           numbersString  = strcat(numbersString, 'e(', num2str(numbers (finish-1)), '/', num2str(numbers (finish-2)),') =   ', num2str(errors(finish-2)),'\\' );
           else
               numbersString  = strcat(numbersString, 'e(',char(numbers (finish-1)), '/', char(numbers (finish-2)),') =   ', char(errors(finish-2)),'\\' );
           end
           numbers(finish-2) =  numbers (finish-1) / numbers (finish-2);
           answeres (k) = numbers(finish-2);
           k = k+1;
           finish = finish-1;
         elseif expression(i)=='-'
           errors(finish-2) =  errors(finish -1) + errors (finish-2); 
           erransweres(k) = errors(finish-2);
           if(isnumeric(a))
           numbersString  = strcat(numbersString, 'e(', num2str(numbers (finish-1)), '-', num2str(numbers (finish-2)),') =   ', num2str(errors(finish-2)),'\\' );
           else
               numbersString  = strcat(numbersString, 'e(',char(numbers (finish-1)), '-', char(numbers (finish-2)),') =   ', char(errors(finish-2)),'\\' );
           end
           numbers(finish-2) =  numbers (finish-1) - numbers (finish-2);
           answeres (k) = numbers(finish-2);
           k=k+1;
           finish = finish-1;
        elseif expression(i)=='^'
           errors(finish-2) =  sqrt((numbers(finish-1) *  numbers(finish-2)^(numbers(finish-1)-1) * errors(finish-2))^2 +(log(numbers(finish-2))*numbers(finish-2)^numbers(finish-1)*errors(finish-1))^2);
           erransweres(k) = errors(finish-2);
           if(isnumeric(a))
           numbersString  = strcat(numbersString, 'e(', num2str(numbers (finish-1)), '^', num2str(numbers (finish-2)),') =   ', num2str(errors(finish-2)),'\\' );
           else
               numbersString  = strcat(numbersString, 'e(',char(numbers (finish-1)), '^', char(numbers (finish-2)),') =   ', char(errors(finish-2)),'\\' );
           end
           numbers(finish-2) =  numbers (finish-2) ^ numbers (finish-1);
           answeres(k) = numbers(finish-2);
           k= k+1;
           finish = finish-1;           
         end
        i = i+1;
        
    end
end
output = numbersString
end