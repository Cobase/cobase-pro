import React, { PropTypes } from 'react';
import { Link } from 'react-router';

export default function SidebarListItem({ title, to, icon }) {
  const classes = `fa ${icon} fa-fw`;

  return (
    <Link
      to={to}
      className="sidebar-navigation-item"
    >
      <i className={classes}></i> {title}
    </Link>
  );
}

SidebarListItem.propTypes = {
  title: PropTypes.string.isRequired,
  to: PropTypes.string.isRequired,
  icon: PropTypes.string.isRequired,
};
