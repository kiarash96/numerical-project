function [intValue] = simpsonOneThird(xs, ys, h)
    n = length(xs);
    intValue = h/3 * (ys(1) + 4 * sum(ys(2:2:n-1)) + 2 * sum(ys(3:2:n-2)) + ys(n));
end
