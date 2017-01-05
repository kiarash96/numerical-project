function [xs, ws, intValue] = gaussLegendre(func, a, b, n)
    syms x;
    P(1) = 1 + x - x;
    P(2) = x;
    for i = 3:n+1
        m = i - 1;
        P(i) = 1/m * ((2*m - 1) * x * P(i - 1) - (m - 1) * P(i - 2));
    end
    m = n + 1;
    xs = double(roots(coeffs(P(m), 'All')));
    Pmp = diff(P(m));
    ws = double(2 ./ ((1 - xs .^ 2) .* (subs(Pmp, xs)) .^ 2));

    f = eval(func);
    fp = (b - a) / 2 * (subs(f,(b-a)/2 * x + (b+a)/2));

    intValue = 0;
    for i = 1:length(xs)
        intValue = intValue + ws(i) * double(subs(fp, xs(i)));
    end
end

