export const GetUserFollowers = userID =>
  fetch(`https://api.github.com/users/${userID}/followers`).then(res =>
    res.json()
  );

export const GetUserFollowing = userID =>
  fetch(`https://api.github.com/users/${userID}/following`).then(res =>
    res.json()
  );
