import React, { PropTypes } from 'react';
import TimeAgo from 'react-timeago';
import Linkify from 'react-linkify';

const Post = ({ createdBy, time, message }) => (
  <section className="post">

    <h3 className="post-title">
      {createdBy}
      <small className="post-datetime">
        <i className="fa fa-clock-o"></i> <TimeAgo date={time} />
      </small>
    </h3>

    <p className="post-content"><Linkify>{message}</Linkify></p>

  </section>
);

Post.propTypes = {
  createdBy: PropTypes.string.isRequired,
  time: PropTypes.string.isRequired,
  message: PropTypes.string.isRequired,
};

export default Post;
