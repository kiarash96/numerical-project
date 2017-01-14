function [xs, ws, ys, result, error] = integrate(func, a, b, h, n, method, addr)

xs = [];
ws = [];
ys = [];
result = '';

func = strcat('@(x)', func);
f = str2func(func);

error = 0;

if (method <= 2 || method == 5)
    n = (b - a) / h;
    xs = [a:h:b];
    ys = arrayfun(f, xs);
    if (method == 0)
        result = trapezoidal(xs, ys, h);
    elseif (method == 1)
        if (mod(n, 2) == 1)
            error = 1;
            result = '\text{N must be even!}';
        else
            result = simpsonOneThird(xs, ys, h);
        end
    elseif (method == 2)
        if (mod(n, 3) ~= 0)
            error = 1;
            result = '\text{N must be a multiple of 3}';
        else
            result = simpsonThreeEight(xs, ys, h);
        end
    elseif (method == 5)
        result = customSimpson(xs, ys, h);
    end
elseif (method == 3)
    result = romberg(f, a, b, n);
elseif (method == 4)
    [xs, ys, ws, result] = gaussLegendre(func, a, b, n);
end

% plot
figure('Visible','off');

xss = [a-1:0.01:b+1];
yss = arrayfun(f, xss);

h = plot(xss, yss);
hold on;
area([a:0.01:b], arrayfun(f, [a:0.01:b]));

ax = gca;
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';
ax.Box = 'off';

saveas(h,addr, 'jpg');

end
