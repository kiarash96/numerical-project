function [] = fig(roots, values,func, address)
str = '@(x)';
func = strcat(str, func);
f = str2func(func);
figure('Visible','off');
n = length(roots)
x = min(roots)-1:0.0001: max(roots)+1;
y = f(x);
h = plot(x, y);
hold on;
scatter(roots(1:n-1), values(1:n-1));
hold on;
scatter( roots(n), values(n));

ax = gca;
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';

ax.Box = 'off';

saveas(h,address, 'jpg');
end