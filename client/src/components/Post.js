import React from 'react';
import TimeAgo from 'react-timeago';

export default ({username, time, message}) => (
  <section className="post">

    <h3 className="post-title">
      {username}
      <small className="post-datetime"><i className="fa fa-clock-o"></i> <TimeAgo date={time}/></small>
    </h3>

    <p className="post-content">{message}</p>

  </section>
);