function [] = fig(roots, values,f, address)
n = length(roots)
x = min(roots)-1:0.0001: max(roots)+1;
y = f(x);
h = figure;
plot(x, y);
hold on;
scatter(roots(1:n-1), values(1:n-1));
hold on;
scatter( roots(n), values(n));
saveas(h,address, 'jpg');
end