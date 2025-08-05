let(
  status, prop("상태"),
  startDate, if(empty(prop("시작일")), false, prop("시작일")),
  dueDate, if(empty(prop("완료일")), false, prop("완료일")),
  releaseDate, if(empty(prop("배포일")), false, prop("배포일")),
  today, today(),

  daysToStart, if(startDate != false, dateBetween(startDate, today, "days"), false),
  daysToDue, if(dueDate != false, dateBetween(dueDate, today, "days"), false),
  daysToRelease, if(releaseDate != false, dateBetween(releaseDate, today, "days"), false),

  if(status == "작업 백로그" or status == "릴리즈" or status == "작업완료", "E",

    if(status == "작업대기",
      if(startDate == false, "E",
        if(daysToStart < 0, "B",
          if(daysToStart == 0, "C",
            "D"
          )
        )
      ),

    if(status == "작업중",
      if(dueDate == false, "B",
        if(daysToDue < 0, "A",
          if(daysToDue == 0, "B",
            "C"
          )
        )
      ),

    if(status == "재확인요청" or status == "리뷰중" or status == "QA 요청" or status == "QA 중",
      if(releaseDate == false,
        if(dueDate == false, "B",
          if(daysToDue < 0, "A",
            if(daysToDue == 0, "B",
              "C"
            )
          )
        ),
        if(daysToRelease < 0, "B",
          if(daysToRelease == 0, "B",
            "C"
          )
        )
      ),

    "A"
    ))))
)