function [T] = romberg(f, a, b, n)
    T = zeros(n, n + 1);
    for i = [1:n]
        h = (b - a) ./ (2 .^ (i - 1));
        T(i, 1) = h;
        T(i, 2) = trapezoidal([a:h:b], arrayfun(f, [a:h:b]), h);
    end
    for i = [1:n-1]
        S = zeros(n, 1);
        for j = [i+1:n]
            S(j) = (4^i * T(j, i + 1) - T(j - 1, i + 1)) / (4^i - 1);
        end
        T(:, i + 2) = S;
    end
end

