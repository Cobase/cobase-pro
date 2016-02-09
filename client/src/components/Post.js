import React from 'react';
import TimeAgo from 'react-timeago';

export default ({username, time, message}) => (
  <section className="panel">

    <h3 className="panel-title">
      {username}
      <small className="panel-datetime"><TimeAgo date={time}/></small>
    </h3>

    <p className="panel-content">{message}</p>

  </section>
);
