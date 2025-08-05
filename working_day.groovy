lets(
    startDay, if(hour(now()) < 18, today(), dateAdd(today(), 1, "days")),
    endDay, if(empty(prop("완료일")), prop("배포일"), prop("완료일")),

    totalDays, dateBetween(endDay, startDay, "days") + 1,
    fullWeeks, floor(totalDays / 7),
    remainingDays, mod(totalDays, 7),

    startWeekday, day(startDay),
    weekendExtra,
        if(startWeekday <= 6 and startWeekday + remainingDays > 6, 1, 0)
        + if(startWeekday + remainingDays > 7, 1, 0),
    totalDays - fullWeeks * 2 - weekendExtra
)