function [intValue] = customSimpson(xs, ys, h)
    n = length(xs);
    if (mod(n, 2) == 1)
        intValue = simpsonOneThird(xs, ys, h);
    else
        intValue = simpsonOneThird(xs(1:n-3), ys(1:n-3), h) + simpsonThreeEight(xs(n-3:n), ys(n-3:n), h);
    end
end

