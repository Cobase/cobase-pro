import React, { PropTypes } from 'react';

const Topbar = ({ title }) => (
  <header className="topbar">
    <h2 className="topbar-title">{title}</h2>
  </header>
);

Topbar.propTypes = {
  title: PropTypes.string.isRequired,
};

export default Topbar;
