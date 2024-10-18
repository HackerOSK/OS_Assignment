#include <stdio.h>

void PrintNum(int n)
{
    if (n == 1)
    {
        return;
    }
    PrintNum(--n);
    printf("\n%d", n);
}

int main()
{
    int n;
    scanf("%d", &n);
    PrintNum(n + 1);
}
