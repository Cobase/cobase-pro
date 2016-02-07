import React from 'react';
import TimeAgo from 'react-timeago';

export default ({username, time, message}) => (
  <div>
    <h3>{username} <small><TimeAgo date={time}/></small></h3>
    <p>{message}</p>
  </div>
);
