function [xs, ys, intValue] = trapezoidal(f, a, b, h)
    xs = [a:h:b];
    n = length(xs);
    ys = arrayfun(f, xs);
    intValue = h/2 * (2 * sum(ys) - ys(1) - ys(n));
end

