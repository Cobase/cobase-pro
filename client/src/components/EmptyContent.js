import React, { PropTypes } from 'react';

const EmptyContent = ({ message }) => (
  <div className="empty-content">
    <h3 className="empty-content-message">{message}</h3>
  </div>
);

EmptyContent.propTypes = {
  message: PropTypes.string.isRequired,
};

export default EmptyContent;
